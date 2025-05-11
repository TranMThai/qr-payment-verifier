import { useState } from 'react';
import { callSpeak } from '../api/tts-api';
import sound from '../assets/prefix-sound.wav'


const useTTS = () => {

    const [audio, _] = useState<HTMLAudioElement>(new Audio())
    const [prefixSound, setPrefixSound] = useState<HTMLAudioElement | null>(null)

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

    const speak = async (text: string) => {
        const speech = await callSpeak(text)
        const audioUrl = URL.createObjectURL(speech)
        audio.src = audioUrl
        await playPrefixSound()
        audio.play();
        return audio
    }

    const speakByByte = async (text: string) => {
        const binaryString = window.atob(text);
        const len = binaryString.length;
        const bytes = new Uint8Array(len);

        for (let i = 0; i < len; i++) {
            bytes[i] = binaryString.charCodeAt(i);
        }
        const blob = new Blob([bytes], { type: "audio/wav" });
        const audioUrl = URL.createObjectURL(blob);

        audio.src = audioUrl
        await playPrefixSound()
        audio.play();
        return audio
    }

    return { speak, speakByByte, waitForAudioToEnd }
}

export default useTTS