import { StorageContentType } from '@/types/storage-content-type.type';
import { StorageDomain } from '@/types/storage-domain.type';

export type GeneratePresignedUrlREQ = {
  storageDomain: StorageDomain;
  storageContentType: StorageContentType;
};
