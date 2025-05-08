import { useState } from 'react';
import { callSpeak } from '../api/tts-api';


const useTTS = () => {

    const [audio, _] = useState<HTMLAudioElement>(new Audio())

    const speak = async (text: string) => {
        const speech = await callSpeak(text)
        const audioUrl = URL.createObjectURL(speech.data)
        audio.src = audioUrl
        audio.play();
        return audio
    }

    return { speak }
}

export default useTTS