set PGPASSWORD=postgres123
cd util
pg_restore.exe -c --host localhost --port 5432 --username "postgres" --dbname "fitsys" --verbose --no-password "bkp.backup"