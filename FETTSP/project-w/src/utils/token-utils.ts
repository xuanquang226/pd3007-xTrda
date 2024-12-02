import Cookies from "js-cookie";

export const setTokenToCookie = (token:string) => {
    Cookies.set("accessToken", token, {expires: 1});
}

export const setRefreshTokenToCookie = (refreshToken: string) => {
    Cookies.set("refreshToken", refreshToken, {expires: 7});
}

export const getTokenFromCookie = () => {
    return Cookies.get("accessToken");
}

export const getRefreshTokenFromCookie = () => {
    return Cookies.get("refreshToken");
}

export const removeToken = () => {
    Cookies.remove("accessToken");
    Cookies.remove("refreshToken");
}