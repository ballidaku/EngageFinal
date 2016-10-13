LOCAL_PATH := $(call my-dir)
MY_LOCAL_PATH := $(LOCAL_PATH)
include $(CLEAR_VARS)

LOCAL_MODULE := HelloJNI
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
HelloJNI.c \
/home/brst-pc93/Desktop/EngageAndroid1.2/Engage1.2/app/src/main/jni/HelloJNI.c \


LOCAL_C_INCLUDES += /home/brst-pc93/Desktop/EngageAndroid1.2/Engage1.2/app/src/main/jni
LOCAL_C_INCLUDES += /home/brst-pc93/Desktop/EngageAndroid1.2/Engage1.2/app/src/debug/jni



include $(BUILD_SHARED_LIBRARY)
