android {
	defaultConfig {
	  multiDexEnabled true
	}
	
	repositories {
        flatDir {
            dirs '../vito_base_lib/libs'
        }
    }

    sourceSets {
        main {
            jniLibs.srcDirs = ['libs']
        }
    }
}

dependencies {
  compile project(':vito_base_lib')
  compile 'com.android.support:multidex:1.0.1'
}
