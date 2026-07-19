import { downloadExcelFileApi } from '@/store/download-excel/download-excel.api';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { downloadExcel } from '@/utils/utils/download-excel.util';
import { useMutation } from '@tanstack/react-query';

type useDownloadExcelProps<T> = {
  fileName: string;
  url?: string;
};

type DownloadExcelParams<T> = {
  filter: T;
  ids?: string;
  dynamicUrl?: string;
  dynamicName?: string;
  startDate?: number;
  endDate?: number;
};

const useDownloadExcel = <T>({ fileName, url }: useDownloadExcelProps<T>) => {
  const { mutate: mutateDownloadExcel } = useMutation({
    mutationFn: ({ filter, ids, startDate, endDate, dynamicUrl, dynamicName }: DownloadExcelParams<T>) =>
      downloadExcelFileApi(url ?? dynamicUrl ?? '', { filter, ids }, startDate, endDate),
    onSuccess: (resp, { dynamicName }) => {
      downloadExcel(resp, dynamicName ?? fileName);
    },
    onError: handleError,
  });

  return { mutateDownloadExcel };
};

export default useDownloadExcel;
