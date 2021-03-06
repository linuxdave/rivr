/*
 * Copyright (c) 2002-2010 Nu Echo Inc. All rights reserved.
 */

package com.nuecho.rivr.voicexml.turn.output.interaction;

import static java.util.Arrays.*;

import java.util.*;

import javax.json.*;

import com.nuecho.rivr.core.util.*;
import com.nuecho.rivr.voicexml.turn.output.audio.*;
import com.nuecho.rivr.voicexml.util.json.*;

/**
 * An <code>InteractionRecognition</code> is an optional final phase of an
 * {@link InteractionTurn}.
 * <p>
 * It specifies a recognition configuration, and optionally, a no input timeout
 * and a sequence of {@link AudioItem} that is played if a recognition is
 * successful.
 * 
 * @author Nu Echo Inc.
 */
public final class InteractionRecognition implements JsonSerializable {
    private static final String NO_INPUT_TIMEOUT_PROPERTY = "noInputTimeout";
    private static final String SPEECH_RECOGNITION_CONFIGURATION_PROPERTY = "speechRecognitionConfiguration";
    private static final String DTMF_RECOGNITION_CONFIGURATION_PROPERTY = "dtmfRecognitionConfiguration";
    private static final String ACKNOWLEDGE_AUDIO_ITEMS_PROPERTY = "acknowledgeAudioItems";

    private final SpeechRecognitionConfiguration mSpeechRecognitionConfiguration;
    private final DtmfRecognitionConfiguration mDtmfRecognitionConfiguration;
    private TimeValue mNoInputTimeout;
    private final List<AudioItem> mAcknowledgeAudioItems;

    public InteractionRecognition(DtmfRecognitionConfiguration dtmfRecognitionConfiguration,
                                  SpeechRecognitionConfiguration speechRecognitionConfiguration,
                                  TimeValue noInputTimeout,
                                  AudioItem... acknowledgeAudioItems) {
        this(dtmfRecognitionConfiguration,
             speechRecognitionConfiguration,
             noInputTimeout,
             asList(acknowledgeAudioItems));
    }

    public InteractionRecognition(DtmfRecognitionConfiguration dtmfRecognitionConfiguration,
                                  SpeechRecognitionConfiguration speechRecognitionConfiguration,
                                  TimeValue noInputTimeout,
                                  List<? extends AudioItem> acknowledgeAudioItems) {
        mSpeechRecognitionConfiguration = speechRecognitionConfiguration;
        mDtmfRecognitionConfiguration = dtmfRecognitionConfiguration;
        mNoInputTimeout = noInputTimeout;
        mAcknowledgeAudioItems = new ArrayList<AudioItem>(acknowledgeAudioItems);
        assertInvariant();
    }

    public SpeechRecognitionConfiguration getSpeechRecognitionConfiguration() {
        return mSpeechRecognitionConfiguration;
    }

    public DtmfRecognitionConfiguration getDtmfRecognitionConfiguration() {
        return mDtmfRecognitionConfiguration;
    }

    public TimeValue getNoInputTimeout() {
        return mNoInputTimeout;
    }

    public List<? extends AudioItem> getAcknowledgeAudioItems() {
        return Collections.unmodifiableList(mAcknowledgeAudioItems);
    }

    public void setNoInputTimeout(TimeValue noInputTimeout) {
        mNoInputTimeout = noInputTimeout;
    }

    private void assertInvariant() {
        Assert.ensure(mSpeechRecognitionConfiguration != null || mDtmfRecognitionConfiguration != null,
                      "Must provide a non-null configuration for speech or DTMF");
    }

    @Override
    public JsonValue asJson() {
        JsonObjectBuilder builder = JsonUtils.createObjectBuilder();
        JsonUtils.add(builder, ACKNOWLEDGE_AUDIO_ITEMS_PROPERTY, JsonUtils.toJson(mAcknowledgeAudioItems));
        JsonUtils.add(builder, DTMF_RECOGNITION_CONFIGURATION_PROPERTY, mDtmfRecognitionConfiguration);
        JsonUtils.add(builder, SPEECH_RECOGNITION_CONFIGURATION_PROPERTY, mSpeechRecognitionConfiguration);
        JsonUtils.addTimeProperty(builder, NO_INPUT_TIMEOUT_PROPERTY, mNoInputTimeout);
        return builder.build();
    }

    @Override
    public String toString() {
        return asJson().toString();
    }
}