#!/bin/bash
if [ ! -d "out" ]; then
  mkdir out
fi
javac -sourcepath ./src -d ./out ./src/Main.java
