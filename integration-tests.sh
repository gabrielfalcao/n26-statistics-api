#!/bin/bash

host="http://127.0.0.1:8000"


function send-transaction() {
    amount="${1}"
    timestamp=$(date +%s)
    sleep 0.1
    payload="{\"timestamp\": ${timestamp}, \"amount\": ${amount}.0}"
    echo "POST ${host}/transactions -> ${payload}"
    curl -s -XPOST "${host}/transactions" -H 'Content-Type: application/json' -d "${payload}"
    return
}

function get-statistics() {
    curl -s -XGET "${host}/statistics" -H 'Content-Type: application/json'
    return
}

function reset-statistics() {
    curl -s -XDELETE "${host}/statistics" -H 'Content-Type: application/json'
    return
}

function assure-key-matches-value() {
    name="${1}"
    shift
    expected_value="${1}"
    shift
    get-statistics > test-statistics.json

    if (grep "\"${name}\"[:]${value}" test-statistics.json); then
        return 0;
    fi

    echo -e "\033[1;33m${data}\033[0m"
    echo -e "\033[1;31mField \033[1;37m'${name}'\033[1;31mdoes not match ${value}\033[0m"
    exit 1
}

reset-statistics

# Given that I send 7 transactions with the amount of 100
send-transaction 100
send-transaction 100
send-transaction 100
send-transaction 100
send-transaction 100
send-transaction 100
send-transaction 100
send-transaction 100

# And I send 2 transactions with the amount of 50
send-transaction 50
send-transaction 50

# And I send 1 transaction with the amount of 200
send-transaction 200

# When I prepare to assert
set -e

# Then the sum is 1000
assure-key-matches-value "sum" "1000.0"

# Then the max is 200
assure-key-matches-value "max" "200.0"

# Then the min is 50
assure-key-matches-value "min" "50.0"

# Then the avg is 100
assure-key-matches-value "avg" "100.0"

# Then the count is 10
assure-key-matches-value "count" "10"

echo "All tests passed"
