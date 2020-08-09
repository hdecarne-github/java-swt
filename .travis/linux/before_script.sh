#!/bin/bash -x

export DISPLAY=":99.0"
export DBUS_SESSION_BUS_ADDRESS="/dev/null"

/sbin/start-stop-daemon --start --quiet --pidfile /tmp/custom_xvfb_99.pid --make-pidfile --background --exec /usr/bin/Xvfb -- :99 -ac -screen 0 1920x1280x16
sleep 10 # give fb some time to start

fluxbox >/dev/null 2>&1 &
sleep 10 # give wm some time to start
