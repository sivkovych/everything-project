#!/usr/bin/env sh

get_version() {
  echo "$1" |
    grep -Po "(?<=^$2<version>)((\d+|.)+?)(?=<\/version>)" |
    sed -e 's|\.||g'
}
get_old_version() {
  echo get_version "$1" "-"
}
get_new_version() {
  echo get_version "$1" "\+"
}

file_name="pom.xml"
base_ref=$1

git fetch -q origin "${base_ref}":refs/remotes/origin/"${base_ref}"

path=$(git diff --name-only origin/"${base_ref}" | grep "${file_name}")

if [ -z "${path}" ]; then
  echo "Cannot find [${file_name}] in changed files" >>/dev/stderr
  exit 1
else
  diff=$(git diff origin/"${base_ref}" "${path}" | sed -e 's| ||g')
  old_version=$(get_old_version "$diff")
  new_version=$(get_new_version "$diff")
  difference=$((new_version - old_version))
  if [ ${difference} -gt 0 ]; then
    echo "Version was incremented by ${difference}"
    exit 0
  else
    echo "Version was changed but not incremented" >>/dev/stderr
    exit 1
  fi
fi
