import { LOCAL_STORAGE } from '@/utils/constants/storage.constant';

export const setLocalAccessToken = (accessToken: string) => {
  localStorage.setItem(LOCAL_STORAGE.ACCESS_TOKEN, accessToken);
};

export const getLocalAccessToken = () => {
  return localStorage.getItem(LOCAL_STORAGE.ACCESS_TOKEN);
};

export const removeLocalAccessToken = (): void => {
  localStorage.removeItem(LOCAL_STORAGE.ACCESS_TOKEN);
};
