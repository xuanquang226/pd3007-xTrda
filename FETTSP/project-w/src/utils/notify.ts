import { ToastContainer, toast } from 'react-toastify';

export const notifySuccess = (content: string) => {
    toast.success(content, {
        position: "top-right",
        autoClose: 3000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: false,
        draggable: true,
        progress: undefined,
      });
}

export const notifyError = (content: string) => {
    toast.error(content, {
        position: "top-right",
        autoClose: 3000,
        hideProgressBar:false,
        closeOnClick: true,
        pauseOnHover: false,
        draggable: false,
        progress: undefined,
    });
}