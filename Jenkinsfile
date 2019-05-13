#!/usr/bin/env groovy

pipeline {
    agent { docker { image 'python:3.5.1' } }
    stages {
		stage('list') {
			steps {
               sh 'echo "versions - list"'
			}
		}
 
    stages {
		stage('version') {
			steps {
               getTags()
			}
		}
        stage('build') {
            steps {
                sh 'python --version'
            }
        }
    }
}

def getTags() {
    def gitTagOutput = sh(script: "git tag", returnStdout: true)
    def tags = gitTagOutput.split("\n").findAll{ it =~ /^\d+\.\d+\.\d+$/ }
    return tags
}


def call(version, semantic) {

    echo "bumping up version: $version"

    def parser = /(?<major>v\d+).(?<minor>\d+).(?<revision>\d+)/
    def match = version =~ parser
    match.matches()
    def (major, minor, revision) = ['major', 'minor', 'revision'].collect { match.group(it) }

    nextVersion = version
    if(semantic == "major") {
        echo "bumping up major version ${major}"
        nextVersion = "${(major.toInteger() + 1)}" + "." + 0 + "." + 0
    } else if(semantic == "minor") {
        echo "bumping up minor version ${minor}"
        nextVersion = "${major}" + "." + "${(minor.toInteger() + 1)}" + "." + 0
    } else {
        echo "bumping up revision version ${revision}"
        nextVersion = "${major}" + "." + "${minor}" + "." + "${revision.toInteger() + 1}"
    }

    echo "next version is set to: $nextVersion"

    return nextVersion
}
