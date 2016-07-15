echo 'Java code line count:'
ls -include *.java -recurse|gc|measure|select -expand count
echo 'FXML code line count:'
ls -include *.fxml -recurse|gc|measure|select -expand count
echo 'Total line count:'
ls -include *.java,*.fxml -recurse|gc|measure|select -expand count
pause