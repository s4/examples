#! /bin/bash

osx=false
case "`uname`" in
Darwin*) osx=true;;
esac

if $osx; then
    READLINK="stat"
else
    READLINK="readlink" 
fi

CP_SEP=":"

BASE_DIR=`dirname $($READLINK -f $0)`
echo $BASE_DIR

CLASSPATH=$CLASSPATH$CP_SEP`find $BASE_DIR/../lib -name "*.jar" | awk '{p=$0"'$CP_SEP'"p;} END {print p}'`
echo $CLASSPATH

CMD="java -classpath $CLASSPATH io.s4.example.twittertopiccount.ui.TwitterUI /tmp/top_n_hashtags"
echo "RUNNING $CMD"

exec ${CMD}
