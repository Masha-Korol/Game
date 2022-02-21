#!/bin/sh

BOT_URL="https://api.telegram.org/bot${TELEGRAM_CI_BOT_TOKEN}/sendMessage"

if [ "$TRAVIS_TEST_RESULT" -ne 0 ]; then
    build_status="failed"
else
    build_status="succeeded"
fi

send_msg () {
    curl -s -X POST "${BOT_URL}" -d chat_id="$TELEGRAM_ALERTS_CHAT_ID" \
        -d text="$1" -d parse_mode="Markdown"
}

send_msg "
-------------------------------------
Build                   *${build_status}!*
\`Repository:  ${TRAVIS_REPO_SLUG}\`
\`Branch:      ${TRAVIS_BRANCH}\`
[Log here](${TRAVIS_JOB_WEB_URL})
--------------------------------------
"