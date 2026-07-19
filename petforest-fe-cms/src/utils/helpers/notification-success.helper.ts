import { toast } from 'react-toastify';

export function handleSuccess(type?: 'create' | 'update' | 'delete' | 'copy' | 'message' | 'download' | 'email') {
  if (type) {
    if (type === 'create') toast.success('성공적으로 생성되었습니다');
    if (type === 'update') toast.success('업데이트가 성공적으로 완료되었습니다');
    if (type === 'copy') toast.success('복사되었습니다.');
    if (type === 'delete') toast.success('성공적으로 삭제되었습니다');
    if (type === 'message') toast.success('메시지가 성공적으로 전송되었습니다');
    if (type === 'download') toast.success('성공적으로 다운로드되었습니다');
    if (type === 'email') toast.success('이메일을 성공적으로 보냈습니다');
  } else toast.success('성공.');
}
