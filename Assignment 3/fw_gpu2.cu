#include <assert.h>
#include <cuda.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/time.h>
#include <cooperative_groups.h>
#include <cuda_runtime_api.h> 
#include "workshop.h"

#define GRAPH_SIZE 2048
//#define GRAPH_SIZE 1024
//#define GRAPH_SIZE 512

#define THREADS_PER_BLOCK 32
#define BLOCKS GRAPH_SIZE/THREADS_PER_BLOCK

#define EDGE_COST(graph, graph_size, a, b) graph[a * graph_size + b]
#define D(a, b) EDGE_COST(output, graph_size, a, b)

#define INF 0x1fffffff

/*void generate_random_graph(int *output, int graph_size) {
  int i, j;

  srand(0xdadadada);

  for (i = 0; i < graph_size; i++) {
    for (j = 0; j < graph_size; j++) {
      if (i == j) {
        D(i, j) = 0;
      } else {
        int r;
        r = rand() % 40 + 6;
        if (r > 20) {
          r = INF;
        }

        D(i, j) = r;
      }
    }
  }
}*/

//NEW ARRAY GEN
void generate_random_graph(int *output, int graph_size) {
  int i, j;

  srand(0xdadadada);

  for (i = 0; i < graph_size; i++) {
    for (j = 0; j < graph_size; j++) {
      if (i == j) {
        D(i, j) = 0;
      } else {
        D(i, j) = i * j;
        if (i % 2==0)
          D(i, j) += j * 4;
      }
    }
  }
}

__global__ void floyd_warshall_kernel(int graph_size, int *output) {
  //WORKS BUT SEEMS A BIT SLOWER
  int i = blockIdx.x;
  int j = threadIdx.x;
  cooperative_groups::grid_group g = cooperative_groups::this_grid();
  
  for(int k = 0; k < graph_size; k++) {
    while(i < graph_size) {
      while(j < graph_size) {
        if(D(i, k) + D(k, j) < D(i, j))
          D(i, j) = D(i, k) + D(k, j);
        j += blockDim.x;
        __syncthreads();
      }
      j = threadIdx.x;
      i += gridDim.x;
    }
    i = blockIdx.x;
    g.sync();
  }

}

void floyd_warshall_gpu(const int *graph, int graph_size, int *output) {
  int *dev_output_gpu;

  int size = sizeof(int) * graph_size * graph_size;

  HANDLE_ERROR(cudaMalloc(&dev_output_gpu, size));
  HANDLE_ERROR(cudaMemcpy(dev_output_gpu, graph, size, cudaMemcpyHostToDevice));

  int graph_size_tmp = GRAPH_SIZE;
  void *kernelArgs[] = { &graph_size_tmp, &dev_output_gpu };
  dim3 dimBlock(THREADS_PER_BLOCK, 1, 1);
  dim3 dimGrid(GRAPH_SIZE/dimBlock.x, 1, 1);
  cudaLaunchCooperativeKernel((void*)floyd_warshall_kernel, dimGrid, dimBlock, kernelArgs);

  cudaDeviceSynchronize();

  printf("Device Variable Copying:\t%s\n", cudaGetErrorString(cudaGetLastError()));

  HANDLE_ERROR(cudaMemcpy(output, dev_output_gpu, size, cudaMemcpyDeviceToHost));
  cudaFree(dev_output_gpu);
}

void floyd_warshall_cpu(const int *graph, int graph_size, int *output) {
  int i, j, k;

  memcpy(output, graph, sizeof(int) * graph_size * graph_size);

  for (k = 0; k < graph_size; k++) {
    for (i = 0; i < graph_size; i++) {
      for (j = 0; j < graph_size; j++) {
        if (D(i, k) + D(k, j) < D(i, j)) {
          D(i, j) = D(i, k) + D(k, j);
        }
      }
    }
  }
}

int main(int argc, char **argv) {
#define TIMER_START() gettimeofday(&tv1, NULL)
#define TIMER_STOP()                                                           \
  gettimeofday(&tv2, NULL);                                                    \
  timersub(&tv2, &tv1, &tv);                                                   \
  time_delta = (float)tv.tv_sec + tv.tv_usec / 1000000.0

  struct timeval tv1, tv2, tv;
  float time_delta;

  int *graph, *output_cpu, *output_gpu;

  int size;
  size = sizeof(int) * GRAPH_SIZE * GRAPH_SIZE;

  graph = (int *)malloc(size);
  assert(graph);

  output_cpu = (int *)malloc(size);
  assert(output_cpu);
  memset(output_cpu, 0, size);

  output_gpu = (int *)malloc(size);
  assert(output_gpu);

  generate_random_graph(graph, GRAPH_SIZE);

  printf("BLOCKS: %d\nTHREADS PER BLOCK: %d\n\n", BLOCKS, THREADS_PER_BLOCK);

  fprintf(stderr, "running on cpu...\n");
  TIMER_START();
  floyd_warshall_cpu(graph, GRAPH_SIZE, output_cpu);
  TIMER_STOP();
  fprintf(stderr, "%f secs\n", time_delta);

  fprintf(stderr, "running on gpu...\n");
  TIMER_START();
  floyd_warshall_gpu(graph, GRAPH_SIZE, output_gpu);
  TIMER_STOP();
  fprintf(stderr, "%f secs\n", time_delta);

  //if (memcmp(output_cpu, output_gpu, size) != 0)
  //  fprintf(stderr, "FAIL!\n");

  for(int i = 0; i < GRAPH_SIZE; i++) {
    if(output_cpu[i] != output_gpu[i]) {
      printf("FAIL: Values do not match..\n");
      printf("Index: %d   CPU: %d   GPU %d\n", 0, output_cpu[0], output_gpu[0]);
      printf("Index: %d   CPU: %d   GPU %d\n", i, output_cpu[i], output_gpu[i]);
      break;
    }
  }
  //printf("SUCCESSFUL: It's correct!\n");

  return 0;
}

