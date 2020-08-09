#!/bin/bash -x

/sbin/start-stop-daemon --start --quiet --pidfile /tmp/custom_xvfb_99.pid --make-pidfile --background --exec /usr/bin/Xvfb -- :99 -ac -screen 0 1920x1280x16
sleep 10 # give programs some time to start
fluxbox >/dev/null 2>&1 &
sleep 10 # give programs some time to start
