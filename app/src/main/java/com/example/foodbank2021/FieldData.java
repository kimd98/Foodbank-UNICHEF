/**
 Copyright (c) 2012-2017, Smart Engines Ltd
 All rights reserved.
 Redistribution and use in source and binary forms, with or without modification,
 are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice,
 this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.
 * Neither the name of the Smart Engines Ltd nor the names of its
 contributors may be used to endorse or promote products derived from this
 software without specific prior written permission.
 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.example.foodbank2021;

import android.graphics.Bitmap;

public class FieldData {    // stored field data
    private String name;
    private String value;

    private Bitmap image = null;

    private boolean isImage = false;
    private boolean accepted = false;

    public FieldData(String name, Bitmap photo, boolean accepted) {
        this.name = name;
        this.image = photo;
        this.accepted = accepted;
        this.isImage = true;
    }

    public FieldData(String name, String value, boolean accepted) {
        this.name = name;
        this.value = value;
        this.accepted = accepted;
        this.isImage = false;
    }

    public String name() {
        return name;
    }
    public String value() {
        return value;
    }
    public Bitmap image() { return image; }
    public boolean accepted() {
        return accepted;
    }
    public boolean isImage() {
        return isImage;
    }

}
