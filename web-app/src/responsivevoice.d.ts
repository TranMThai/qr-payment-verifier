declare global {
  interface Window {
    responsiveVoice: {
      speak: (text: string, voice?: string, params?: any) => void;
      cancel: () => void;
      isPlaying: () => boolean;
    };
  }
}

export {};
