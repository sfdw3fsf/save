import DownloadIcon from '@/components/icons/DownloadIcon';
import FileIcon from '@/components/icons/FileIcon';
import { useDownloadLink } from '@/hooks/useDownloadLink';
import { Attachment } from '@/types/attachment.type';
import { getFileExtension } from '@/utils/helpers/file.helper';

type DownloadItemProps = {
  attachement: Attachment;
};
const DownloadItem = ({ attachement }: DownloadItemProps) => {
  const onDownload = useDownloadLink;
  return (
    <div className='flex min-w-60 items-center justify-between   p-3 border-slate-200 bg-white rounded-lg border'>
      <div className='flex items-center gap-6'>
        <FileIcon variant={getFileExtension(attachement?.name ?? 'example.png')} />
        <div>
          <p className='text-sm text-[#0A0D14]  line-clamp-1'>{attachement?.name}</p>
          <p className='text-xs text-slate-500'>{attachement?.size ?? 0} KB</p>
        </div>
      </div>
      <button className='w-8' onClick={() => onDownload(attachement.url, attachement.name)}>
        <DownloadIcon />
      </button>
    </div>
  );
};

export default DownloadItem;
