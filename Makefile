#
# Alexey Leonchik alexey@leonchik.ru
# For TESTING ONLY
#
# java -cp bin:lib/sqlite-jdbc-3.8.6.jar Pressure
# java -cp .:bin:lib/postgresql-9.4-1200.jdbc4.jar FreeAd
#

JC = javac
JCFLAGS = 
APP = GenPass
PASS = Paass
USER = Yuooser
DAVSRV = https://webdav.yandex.ru/java/

default:	all

all: 		jar

jar: 		classes
	jar -cmf MANIFEST.MF $(APP).jar -C bin .

classes:
	$(JC) $(JCFLAGS) -d ./bin $(APP).java

clean:
	rm -f ./bin/*.class
	rm -f $(APP).jar

zip: 		clean
	zip -r ../$(APP) *

distr:		all
	zip -r -x*.txt ../$(APP)Distr img/* lib/* *.jar *.cfg

put:		distr zip
	curl -# -T ../$(APP).zip --user $(USER):$(PASS) $(DAVSRV) > /dev/null
	curl -# -T ../$(APP)Distr.zip --user $(USER):$(PASS) $(DAVSRV) > /dev/null

get:
	curl -# --user $(USER):$(PASS) -o ../$(APP).zip $(DAVSRV)$(APP).zip
	curl -# --user $(USER):$(PASS) -o ../$(APP)Distr.zip $(DAVSRV)$(APP)Distr.zip
