javac Book.java Library.java Member.java
count=0

for folder in `ls -d testing/*/ | sort -V`; do
  name=$(basename "$folder")

  echo Running test $name

  expected_file=testing/$name/$name.out
  in_file=testing/$name/$name.in

  java Library < $in_file | diff - $expected_file || echo "Test $name failed!\n"
  count=$((count+1))
done

echo Running test 17
diff testing/saveCollection/save.csv testing/saveCollection/compare.csv || echo "Test 17 (comparing outputted files) failed!"
count=$((count+1))

echo "Finished running $count tests!"