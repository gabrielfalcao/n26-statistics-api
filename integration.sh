#!/bin/bash

host="http://127.0.0.1:8000"

function sendTransaction() {
    amount="${1}"
    timestamp=$(date '+%s')
    curl -XPOST "${host}/transactions" -H 'Content-Type: application/json' -d "{\"timestamp\": ${timestamp}, \"amount\": ${amount}}"
    return
}


sendTransaction 100
sendTransaction 100
sendTransaction 100
sendTransaction 100
sendTransaction 100
sendTransaction 100
sendTransaction 100
sendTransaction 100

sendTransaction 50
sendTransaction 50

sendTransaction 200
