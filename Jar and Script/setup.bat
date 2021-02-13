echo 'Running SQL script ...'
mysql --verbose --host=localhost --user=root --password=root < DBSetup.sql
echo 'Starting server...'
start cmd /K java -jar Server.jar
echo 'Server Started!'
echo 'Starting Client...'
start cmd /K java -jar Client.jar localhost 8080
echo 'Client Started!'