import { useRouter } from 'next/navigation';

type HospitalNameProps = {
  name: string;
  id?: number | null;
};

const HospitalName = ({ name, id }: HospitalNameProps) => {
  const router = useRouter();
  const onOpenReservationDetail = () => {
    if (!id) return;
    window.open(`/collaborating/partner-hospital/?id=${id}`, '_blank', 'noopener,noreferrer');
  };
  return (
    <div onClick={onOpenReservationDetail} className='text-id cursor-pointer'>
      {name}
    </div>
  );
};

export default HospitalName;
