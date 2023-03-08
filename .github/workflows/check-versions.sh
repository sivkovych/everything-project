#!/usr/bin/env sh

base_ref=$1
git fetch -q origin "${base_ref}":refs/remotes/origin/"${base_ref}"

path=$(git diff --name-only origin/"${base_ref}" | grep pom.xml)

if [ -z "${path}" ]; then
  echo Cannot find pom.xml in changed files
  exit 1
else
  diff=$(git diff origin/"${base_ref}" "${path}" | sed -e 's| ||g')
  was=$(echo "${diff}" |
    grep -Po "(?<=^-<version>)((\d+|.)+?)(?=<\/version>)" |
    sed -e 's|\.||g')
  become=$(echo "${diff}" |
    grep -Po "(?<=^+<version>)((\d+|.)+?)(?=<\/version>)" |
    sed -e 's|\.||g')
  echo "WAS: ${was}"
  echo "BECOME: ${become}"
  difference=$((become - was))
  echo "DIFFERENCE: ${difference}"
  is_greater=$((difference > 0))
  echo "IS GREATER: ${is_greater}"
  if ${is_greater}; then
    echo "IS OK"
  else
    echo "IS NOT OK"
    exit 1
  fi
fi
