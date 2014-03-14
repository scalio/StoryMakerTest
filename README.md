StoryMakerTest
==============

A test project for [StoryMaker](http://storymaker.cc/) that utilizes Espresso

###Credits

StoryMakerTest was developed by [Scal.io](http://scal.io) and [Small World News](http://smallworldnews.tv/) as part of the [StoryMaker](http://storymaker.cc/) project with the generous support of [Open Technology Fund](https://www.opentechfund.org/).

[StoryMaker - Make your story great](http://storymaker.cc/)

### Setup

**Using Eclipse:**

1. Import the StoryMakerTest project into your StoryMaker Eclipse workspace.

2. Add the StoryMaker project to the Java Build Path of the StoryMakerTest project.

        StoryMaker -> Project Properties -> Java Build Path -> Projects(Tab) -> Add -> Select StoryMaker
        
3. Add a JUnit Test Run Configuration.

	    Run -> Run Configurations -> (Left Pane) StoryMakerTest -> Test (Tab)
	    
   + In the Test tab:
       + Make sure "Run all tests in the selected project" is set to "StoryMakerTest"
       + Make sure "Instrumentation Runner" is set to "com.google.android.apps.common.testing.testrunner.GoogleInstrumentationTestRunner"


#### Using [Spoon](https://github.com/square/spoon)

Spoon automatically runs tests against all devices available to adb and generates pretty HTML reports in `./spoon-output/`. 

To invoke spoon:

    java -jar ./spoon/spoon-runner-1.1.1-jar-with-dependencies.jar \ 
    --class-name info.guardianproject.mrapp.test.TestCreateStory \  # fully qualified test class name. Ignore to run all tests
    --apk ./path/to/StoryMaker.apk \
    --test-apk ./bin/StoryMakerTest.apk

**Note:** `IPTest` in the BouncyCastle library is detected by Spoon if you don't specify a test `--class-name`, and Spoon is unable to run that test.

### Authors

- [Josh Steiner](https://github.com/vitriolix/)
- [Micah Lucas](https://github.com/micahjlucas/)
- [David Brodsky](https://github.com/OnlyInAmerica/)
