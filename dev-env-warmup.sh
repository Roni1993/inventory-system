#!/bin/sh

delivery-service/gradlw assemble
store-service/gradlw assemble
npm --prefix ./inventory-frontend install ./inventory-frontend