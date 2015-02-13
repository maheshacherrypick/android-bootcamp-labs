for filename in *.asc; do
	cdk -b "$filename"
done