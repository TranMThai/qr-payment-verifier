import { useEffect, useState } from 'react';
import { callSpeak } from '../api/tts-api';
import sound from '../assets/prefix-sound.wav'
import { base64ToBlob } from '../utils/file-converter-utils';


const useTTS = () => {

    const [audio, _] = useState<HTMLAudioElement>(new Audio())
    const [prefixSound, setPrefixSound] = useState<HTMLAudioElement | null>(null)

    useEffect(() => {
        audio.onpause = () => { }
    }, [])

    const waitForAudioToEnd = (audio: HTMLAudioElement): Promise<void> => {
        return new Promise((resolve) => {
            audio.onended = () => resolve();
            audio.onerror = () => resolve();
        });
    };

    const getPrefixSound = async () => {
        if (!prefixSound) {
            const res = await fetch(sound);
            const arrayBuffer = await res.arrayBuffer();
            const blob = new Blob([arrayBuffer], { type: 'audio/wav' });
            const url = URL.createObjectURL(blob);
            const audioElement = new Audio(url);
            setPrefixSound(audioElement);
            return audioElement;
        }
        return prefixSound;
    };

    const playPrefixSound = async () => {
        const sound = await getPrefixSound()
        sound.play()
        await waitForAudioToEnd(sound)
    }

    const playSound = async (blob: Blob) => {
        const audioUrl = URL.createObjectURL(blob);
        audio.src = audioUrl
        await playPrefixSound()
        audio.play();
        return audio
    }

    const speak = async (text: string) => {
        const blob = await callSpeak(text)
        const audio = await playSound(blob)
        return audio
    }

    const speakByBase64 = async (base64: string) => {
        const blob = base64ToBlob(base64)
        const audio = await playSound(blob)
        return audio
    }

    return { audio, speak, speakByBase64, waitForAudioToEnd }
}

export default useTTS