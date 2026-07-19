import { AxiosError } from 'axios';
import { enqueueSnackbar } from 'notistack';
import queryString from 'query-string';
import { EXCEPTION_CODE } from '../enums/data/exception-code.enum';

export const getExceptionCode = (error: AxiosError): string | null => {
  if (!error.isAxiosError) return null;
  const exceptionCode = (error as any).response?.data?.exceptionCode;
  return exceptionCode in EXCEPTION_CODE ? exceptionCode : null;
};

export const getErrorMessage = (error: AxiosError | string): string => {
  if (typeof error === 'string') {
    return error;
  }

  if (!error.isAxiosError) {
    return '';
  }

  if (error.response) {
    const data: any = error.response.data;

    if (Array.isArray(data?.message)) {
      return data?.message.map((item: string) => item).join(', ');
    }

    if (typeof data?.message === 'string') {
      const message = data?.message;
      const index = message.indexOf(':');
      return index !== -1 ? message.substring(index + 1).trim() : message;
    }
  }

  if (error.message || (typeof error.message === 'string' && !error.isAxiosError)) {
    return error.message;
  }

  return '';
};

export const onError = (error: AxiosError) => {
  const message = getErrorMessage(error);
  enqueueSnackbar({ variant: 'error', message });
};

export const getParamString = (search: string, name: string): string | null => {
  const param = queryString.parse(search)?.[name];

  if (Array.isArray(param)) return param[0];

  if (typeof param === 'string') return param;

  return null;
};
