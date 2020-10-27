# Download input files from google drive
echo 'Downloading archive...'
file_id='1PY_7TF19FCwaFbuZv6K31l71NXmALYvH'
file_name='hw1 4.zip'
curl -sc /tmp/cookie "https://drive.google.com/uc?export=download&id=${file_id}" > /dev/null
code="$(awk '/_warning_/ {print $NF}' /tmp/cookie)"
curl -Lb /tmp/cookie "https://drive.google.com/uc?export=download&confirm=${code}&id=${file_id}" -o ${file_name}

# Extract archive
echo 'Extracting archive...'
unzip hw1
mkdir input
mkdir cache
cp ./hw1\ 4/imp* input/
cp ./hw1\ 4/city* cache/

# Copy files to hdfs
echo 'Loading files to hdfs...'
hdfs dfs -rm -r input
hdfs dfs -rm -r cache
hdfs dfs -put -f ./input input
hdfs dfs -put -f ./cache cache

# Delete tmp files
echo 'Removing temporary files...'
rm -rf ./__MACOSX/
rm -rf ./hw1\ 4/
rm -f hw1
rm -rf ./input
rm -rf ./cache