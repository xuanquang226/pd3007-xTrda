import {create} from 'zustand';

interface Hbutton {
    isVisible: boolean;
    isClicked: boolean;
    setVisible: (value: boolean) => void;
    toggleClick: () => void;
    setClicked: (value: boolean) => void;
}

const useHButton = create<Hbutton>((set) => ({
   isVisible: false,
   isClicked: false,
   setVisible: (value) => set({isVisible: value}),
   toggleClick: () => set((state) => ({isClicked: !state.isClicked})),
   setClicked: (value) => set({isClicked: value})
}));

export default useHButton;
