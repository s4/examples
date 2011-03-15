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

export S4_APPS=$IMAGE_BASE/s4_apps

BASE_DIR=`dirname $($READLINK -f $0)`
echo $BASE_DIR

rm $S4_APPS/twittertopiccount-*.tar.gz
rm -r $S4_APPS/twittertopiccount
cp $BASE_DIR/../../twittertopiccount-*.tar.gz $S4_APPS
cd $S4_APPS
pwd
tar zxf $S4_APPS/twittertopiccount-*.tar.gz
cd twittertopiccount
cd bin
chmod 755 twitter_topic_count.sh
cd $IMAGE_BASE/bin
./s4_start.sh &
./run_adapter.sh -x -u $S4_APPS/twittertopiccount/lib/twittertopiccount-*.jar -d $S4_APPS/twittertopiccount/adapter_conf.xml &
cd /tmp
touch top_n_hashtags
cd $S4_APPS/twittertopiccount/bin
sleep 4
./twitter_topic_count.sh
