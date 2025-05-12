export const convertBlobToBase64 = (blob: Blob) => {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();

        reader.onloadend = () => {
            resolve(reader.result);
        };

        reader.onerror = reject;
        reader.readAsDataURL(blob);
    });
}

export const base64ToBlob = (base64: string) => {
    const cleanedBase64 = base64.replace(/^data:.*;base64,/, '');
    const binaryString = window.atob(cleanedBase64);
    const len = binaryString.length;
    const bytes = new Uint8Array(len);
    for (let i = 0; i < len; i++) {
        bytes[i] = binaryString.charCodeAt(i);
    }
    const blob = new Blob([bytes], { type: "audio/wav" });
    return blob
}