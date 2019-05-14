#!/usr/bin/env groovy

pipeline {
    //agent { docker { image 'python:3.5.1' } }
    agent none
    stages {
//        stage("Deploy to test?") {
//          agent none
//          steps {
//            input message: 'Deploy to test?'
//          }
//        }
		stage('list') {
            agent { docker { image 'python:3.5.1' } }
			steps { 
               sh 'git version'
               sh 'git --version'
			}
		}
		stage('version') {
            agent { docker { image 'python:3.5.1' } }
		    steps {
              script {
               MY_GIT_TAG = getTags()
               MY_NEXT_TAG = nextTag(MY_GIT_TAG, 'major')
               
              }
                
               echo "THIS iS MY TAG ${MY_GIT_TAG}"
               echo "This is my next tag ${MY_NEXT_TAG}"
			 }
		}
        stage('build') {
            agent { docker { image 'python:3.5.1' } }
            steps {
                sh 'python --version'
            }
        }
    }
}

def getTags() {
    //def gitTagOutput = sh(script: "git tag", returnStdout: true)
    def gitTagOutput = sh(returnStdout: true, script: "git tag --sort version:refname | tail -1").trim()
    echo "TAG_OUT: $gitTagOutput"
    def tags = gitTagOutput.split("\n").findAll{ it =~ /^v?\d+\.\d+\.\d+$/ }
    echo "TAG_PARSE: $tags"
    return gitTagOutput
}


def nextTag(version, semantic) {

    echo "bumping up version: $version"

    def parser = /(?<major>\d+).(?<minor>\d+).(?<revision>\d+)/
    def match = version =~ parser
    echo "1"
    match.matches()
    echo "2"
    def (major, minor, revision) = ['major', 'minor', 'revision'].collect { match.group(it) }
    echo "3"
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
