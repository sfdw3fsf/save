export type AuthLoginREQ = {
  username: string;
  password: string;
};

export type AuthChangePasswordREQ = {
  oldPassword: string;
  newPassword: string;
};
