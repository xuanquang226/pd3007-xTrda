import {create} from "zustand";
import Customer from "@/type/customer";

interface UserStore{
    customerStore: Customer;
    addCustomer: (customer: Customer) => void;
}

const useUserStore = create<UserStore>(set => ({
    customerStore: {
        id: 0,
        name: '',
        mail: '',
        location: '',
        phone: '',
        idAccount: 0
    },
    addCustomer: (customer) => (set({customerStore: customer})),
}));

export default useUserStore;