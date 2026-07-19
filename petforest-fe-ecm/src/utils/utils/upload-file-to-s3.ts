import { FileDTO } from '@/store/file/dto/file.dto';
import { getFileApi, uploadFileApi, uploadFileToS3Api } from '@/store/file/file.api';
import { fileRespToDto } from '@/store/file/file.service';
import { useMutation } from '@tanstack/react-query';
import { RcFile } from 'antd/es/upload';
import { UploadFile } from 'antd/lib';
import { useMemo, useState } from 'react';

type UploadFileToS3Props = {
  files: UploadFile<any>[];
  fileName?: string;
  memo?: string;
};

export const useUploadFile = () => {
  const [fileInfo, setFileInfo] = useState<FileDTO>();

  const { mutateAsync: uploadFileMetadata, isPending: isUploading } = useMutation({
    mutationFn: ({ fileName, memo }: Pick<UploadFileToS3Props, 'fileName' | 'memo'>) => uploadFileApi({ fileName, memo }),
    onError: (error: any) => {
      console.log(error);
    },
  });

  const { mutateAsync: uploadFileToS3, isPending: isUploadingToS3 } = useMutation({
    mutationFn: ({ url, file }: { url: string; file: RcFile }) => uploadFileToS3Api(url, file),
    onSuccess: () => {},
    onError: (error: any) => {
      console.log(error);
    },
  });

  const { mutateAsync: getFileInfo, isPending: isGettingInfo } = useMutation({
    mutationFn: (id: number) => getFileApi(id),
    onSuccess: (response) => {
      setFileInfo(fileRespToDto(response.data));
    },
    onError: (error: any) => {
      console.log(error);
    },
  });

  const uploadFile = async ({ files, memo }: UploadFileToS3Props) => {
    try {
      const fileIds: any[] = await Promise.all(
        files.map(async (singleFile) => {
          if (!singleFile.originFileObj) {
            return +singleFile.uid;
          }

          const metadataResponse: any = await uploadFileMetadata({
            fileName: singleFile.name,
            memo,
          });

          const uploadUrl = metadataResponse.data?.data?.url;
          if (!uploadUrl) {
            return singleFile.uid;
          }

          await uploadFileToS3({
            url: uploadUrl,
            file: singleFile.originFileObj,
          });

          return metadataResponse.data.data.fileId;
        }),
      );

      return fileIds;
    } catch (error) {
      console.log(error);
      return [];
    }
  };

  const getFile = async (id: number) => {
    await getFileInfo(id);
    return fileInfo;
  };

  const isPending = useMemo(() => {
    return isUploading || isUploadingToS3 || isGettingInfo;
  }, [isUploading, isUploadingToS3, isGettingInfo]);

  return { uploadFile, getFile, isPending };
};
