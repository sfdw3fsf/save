import { useRouter } from 'next/navigation';

type ReservationCodeProps = {
  code: string;
  id?: number | null;
};

const ReservationCode = ({ code, id }: ReservationCodeProps) => {
  const router = useRouter();
  const onOpenReservationDetail = () => {
    if (!id) return;
    window.open(
      `/funeral/reservation/?id=${id}`,
      '_blank',
      `noopener,noreferrer,width=900,height=${window.screen.availHeight},left=0,top=0`,
    );
  };
  return (
    <div
      onClick={(e) => {
        e.preventDefault();
        onOpenReservationDetail();
      }}
      className='text-id cursor-pointer'>
      {code}
    </div>
  );
};

export default ReservationCode;
