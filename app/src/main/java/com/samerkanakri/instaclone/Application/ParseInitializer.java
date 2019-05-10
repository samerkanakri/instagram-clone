/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.samerkanakri.instaclone.Application;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;


public class ParseInitializer extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
            .applicationId("c5f2227e69c1843b26525422f1532ce86905e259")
            .clientKey("e41591cca1bad1d479b90ce604e8c21b8c6134e0")
            .server("http://18.216.129.45:80/parse/")
            .build()
    );


//    ParseUser.enableAutomaticUser();

//    ParseACL defaultACL = new ParseACL();
//    defaultACL.setPublicReadAccess(true);
//    defaultACL.setPublicWriteAccess(true);
//    ParseACL.setDefaultACL(defaultACL, true);

  }
}
