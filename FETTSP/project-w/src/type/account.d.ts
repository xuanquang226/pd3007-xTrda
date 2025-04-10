import RoleAccount from "./role-account";
export default interface Account{
    id: number;
    userName: string;
    password: string;
    accountType: string;
    idCustomer: number;
    roleAccountList: RoleAccount[];
    status: string;
}