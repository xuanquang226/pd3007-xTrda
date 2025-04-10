import { OrderLine } from "./order-line";
export interface Order{
    id: number;
    idCustomer:number;
    totalPrice:string;
    status:string;
    codeDiscount:string;
    notes:string;
    listOrderLine: OrderLine[];
}