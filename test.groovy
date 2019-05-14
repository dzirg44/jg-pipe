#!/usr/bin/env groovy
def test = nextTag('v1.1.5', "major")

def getTags() {
    //def gitTagOutput = sh(script: "git tag", returnStdout: true)
    def gitTagOutput = sh(returnStdout: true, script: "git tag --sort version:refname | tail -1").trim()
    //echo "TAG_OUT: $gitTagOutput"
    def tags = gitTagOutput.split("\n").findAll{ it =~ /^v?\d+\.\d+\.\d+$/ }
    //echo "TAG_PARSE: $tags"
    return gitTagOutput
}

def extractInts( String input ) {
  a = input.findAll( /\d+/ )*.toInteger()
  println a
  return a
}

def nextTag(version, semantic) {

    //echo "bumping up version: $version"

    def parser = /v?(?<major>\d+).(?<minor>\d+).(?<revision>\d+)/
    def match = version =~ parser
//    println parser
    match.matches()
//    match.group(it)
    println "2"
    def (major, minor, revision) = ['major', 'minor', 'revision'].collect { match.group(it) }
    //echo "3"
    nextVersion = version
    if(semantic == "major") {
        println "bumping up major version ${major}"
        verDigits = extractInts(major)
        nextVersion = "${(major.toInteger()  + 1)}" + "." + 0 + "." + 0
    } else if(semantic == "minor") {
        println "bumping up minor version ${minor}"
        verDigits = extractInts(minor)
        nextVersion = "${major}" + "." + "${(verDigits[0] + 1)}" + "." + 0
    } else {
        println "bumping up revision version ${revision}"
        verDigits = extractInts(revision)
        nextVersion = "${major}" + "." + "${minor}" + "." + "${(verDigits[0] + 1)}"
    }

    println "next version is set to: $nextVersion"

    return nextVersion
}
