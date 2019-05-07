set PGUSER=postgres
set PGPASSWORD=postgres123
cd util
pg_dump.exe --host localhost --port 5432 --format custom --blobs --verbose --file "bkp.backup" "fitsys"

