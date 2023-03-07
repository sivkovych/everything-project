#!/usr/bin/env sh

base=$1
git fetch origin "${base}":refs/remotes/origin/"${base}"

path=$(git diff --name-only origin/"${base}" | grep pom.xml)

if [ -z "${path}" ]; then
  echo Cannot find pom.xml in changed files
  exit 1
else
  diff=$(git diff origin/"${base}" "${path}" | sed -e 's| ||g')
  was=$(echo "${diff}" |
    grep -Po "(?<=^-<version>)((\d+|.)+?)(?=<\/version>)")
  become=$(echo "${diff}" |
    grep -Po "(?<=^-<version>)((\d+|.)+?)(?=<\/version>)")
  echo "${was}"
  echo "${become}"
fi
