mkdir -p tree
rm -rf tree/*


for i in {1..10}
do
    mkdir -p tree/folder_$i
    for j in {1..100}
    do
        mkdir -p tree/folder_$i/file_$j.txt
    done
done
