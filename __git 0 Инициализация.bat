set PATH=C:\Users\user\Desktop\QT_sefl_education\PortableGit\cmd
git init
git add *
git config --global user.email "dema4@mail.ru"
git config --global user.name "Dmitry Kalinin (Chakapon)"
git config core.autocrlf true
rem Automatic replacing LF by CRLF
git remote add my_repo https://github.com/Dema709/Android_TimeLogger
git commit -m "Initial commit"
pause