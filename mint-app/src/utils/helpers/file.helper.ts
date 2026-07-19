import { StorageContentType } from '@/types/storage-content-type.type';

/**
 * Converts an array of MIME types to a string suitable for the `accept` attribute in an HTML input element.
 * @param mimeTypes - Array of MIME types (e.g., ['application/pdf', 'image/jpeg', 'image/png'])
 * @returns A string for the `accept` attribute (e.g., '.pdf, .jpeg, .jpg, .png')
 */
export const convertMimeTypesToAccept = (mimeTypes: string[]): string => {
  const mimeToExtensionMap: { [key: string]: string[] } = {
    'application/pdf': ['.pdf'],
    'image/jpeg': ['.jpeg', '.jpg'],
    'image/png': ['.png'],
    'application/msword': ['.doc'],
    'application/vnd.openxmlformats-officedocument.wordprocessingml.document': ['.docx'],
  };

  const extensions = mimeTypes.flatMap((mimeType) => mimeToExtensionMap[mimeType] || []);
  return extensions.join(', ');
};

/**
 * Converts a file size from kilobytes to a human-readable string with the appropriate size unit.
 * @param sizeInKb - The file size in kilobytes.
 * @returns A string representing the file size in the largest appropriate unit (e.g., '500.00 KB', '1.95 MB').
 */
export const convertFileSizeToString = (sizeInKb: number) => {
  const sizes = ['KB', 'MB', 'GB', 'TB', 'PB'];
  let i = 0;

  while (sizeInKb >= 1024 && i < sizes.length - 1) {
    sizeInKb /= 1024;
    i++;
  }

  return `${sizeInKb.toFixed(2)} ${sizes[i]}`;
};

/**
 * Extracts and returns the file extension from a given filename.
 * @param filename - The name of the file (e.g., 'document.pdf').
 * @returns The file extension as a string (e.g., 'pdf'). If there is no extension, it returns `undefined`.
 */
export const getFileExtension = (filename: string) => {
  return filename.split('.').at(-1);
};

/**
 * Maps a file's MIME type to a corresponding StorageContentType.
 * @param {File} file - The file to evaluate.
 * @returns {StorageContentType | null} - The storage type or null if unsupported.
 */
export const getStorageContentType = (file?: File): StorageContentType | undefined => {
  if (!file) return;

  const mimeType = file.type;

  switch (mimeType) {
    case 'application/pdf':
      return 'PDF';
    case 'application/vnd.rar':
      return 'RAR';
    case 'application/zip':
      return 'ZIP';
    case 'application/x-zip-compressed':
      return 'COMPRESSED_ZIP';
    case 'image/jpeg':
      return 'JPEG';
    case 'image/png':
      return 'PNG';
    case 'text/csv':
      return 'CSV';
    case 'application/vnd.ms-excel':
      return 'EXCEL_XLS';
    case 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet':
      return 'EXCEL';
    case 'application/vnd.openxmlformats-officedocument.wordprocessingml.document':
      return 'WORD';
    case 'application/msword':
      return 'WORD_DOC';
    case 'application/vnd.ms-powerpoint':
      return 'PPT';
    case 'application/vnd.openxmlformats-officedocument.presentationml.presentation':
      return 'PPTX';
    case 'image/svg+xml':
      return 'SVG';
    default:
      return;
  }
};

export const convertTypeToMineType = (type: StorageContentType) => {
  switch (type) {
    case 'PDF':
      return 'application/pdf';
    case 'RAR':
      return 'application/vnd.rar';
    case 'ZIP':
      return 'application/zip';
    case 'COMPRESSED_ZIP':
      return 'application/x-zip-compressed';
    case 'JPEG':
      return 'image/jpeg';
    case 'PNG':
      return 'image/png';
    case 'CSV':
      return 'text/csv';
    case 'EXCEL_XLS':
      return 'application/vnd.ms-excel';
    case 'EXCEL':
      return 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet';
    case 'WORD':
      return 'application/vnd.openxmlformats-officedocument.wordprocessingml.document';
    case 'WORD_DOC':
      return 'application/msword';
    case 'PPT':
      return 'application/vnd.ms-powerpoint';
    case 'PPTX':
      return 'application/vnd.openxmlformats-officedocument.presentationml.presentation';
    case 'SVG':
      return 'image/svg+xml';
    default:
      return '';
  }
};
