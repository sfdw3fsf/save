export const mockDataExperts = [
  {
    userId: 1,
    username: 'johndoe123',
    fullName: 'John Doe',
    email: 'johndoe@example.com',
    phoneNumber: '+1234567890',
    userRole: 'MEMBER',
    userStatus: 'PENDING',
    avatar: {
      key: 'avatar123',
      type: 'PDF',
      name: 'avatar.pdf',
      size: 1024,
      url: 'https://i.pinimg.com/736x/b9/97/a5/b997a530822d0f2c03259070d4590d45.jpg',
    },
    expertId: 101,
    name: 'Dr. John Doe',
    affiliation: 'ABC University',
    yearOfExperience: 15,
    school: 'School of Science',
    department: 'Physics',
    detailedField: 'Quantum Mechanics',
    expertAttachments: [
      {
        key: 'attachment123',
        type: 'PDF',
        name: 'research-paper.pdf',
        size: 2048,
        url: 'https://i.pinimg.com/736x/b9/97/a5/b997a530822d0f2c03259070d4590d45.jpg',
      },
    ],
    businessFieldCategories: [
      {
        id: 1,
        name: 'Healthcare IT Services',
        businessFields: [
          {
            id: 3,
            name: 'Healthcare Data Analytics',
            isOtherField: false,
          },
        ],
      },
      {
        id: 6,
        name: 'Healthcare Consulting',
        businessFields: [
          {
            id: 16,
            name: 'Operational Consulting',
            isOtherField: false,
          },
        ],
      },
      {
        id: 2,
        name: 'Pharmaceuticals',
        businessFields: [
          {
            id: 6,
            name: 'Clinical Trials',
            isOtherField: false,
          },
        ],
      },
      {
        id: 5,
        name: 'Hospital and Healthcare Management',
        businessFields: [
          {
            id: 13,
            name: 'Hospital Administration',
            isOtherField: false,
          },
        ],
      },
    ],
    advisoryFields: [
      {
        id: 1,
        name: 'Research',
        isOtherField: false,
      },
    ],
    experiences: [
      {
        id: 1,
        name: 'Senior Consultant',
        detail: 'Provided expert advice in quantum physics.',
        startMonth: 1,
        startYear: 2010,
        endMonth: 12,
        endYear: 2020,
      },
    ],
    educations: [
      {
        id: 1,
        name: 'PhD in Physics',
        detail: 'Specialized in Quantum Mechanics.',
        entranceMonth: 9,
        entranceYear: 2004,
        graduationMonth: 6,
        graduationYear: 2009,
      },
    ],
  },
  {
    userId: 2,
    username: 'janedoe456',
    fullName: 'Jane Doe',
    email: 'janedoe@example.com',
    phoneNumber: '+9876543210',
    userRole: 'ADMIN',
    userStatus: 'ACTIVE',
    avatar: {
      key: 'avatar456',
      type: 'PNG',
      name: 'avatar.png',
      size: 1500,
      url: 'https://t4.ftcdn.net/jpg/02/69/98/99/360_F_269989951_9Gf7PWaRtrpm2EochO3D5WVn22sFZbNZ.jpg',
    },
    expertId: 102,
    name: 'Prof. Jane Doe',
    affiliation: 'XYZ Institute',
    yearOfExperience: 20,
    school: 'School of Engineering',
    department: 'Electrical Engineering',
    detailedField: 'Artificial Intelligence',
    expertAttachments: [
      {
        key: 'attachment456',
        type: 'DOCX',
        name: 'patent-details.docx',
        size: 3072,
        url: 'https://example.com/patent-details.docx',
      },
    ],
    businessFields: [
      {
        id: 3,
        name: 'Technology',
        isOtherField: false,
      },
    ],
    advisoryFields: [
      {
        id: 2,
        name: 'AI Research',
        isOtherField: true,
      },
    ],
    experiences: [
      {
        id: 2,
        name: 'Lead Engineer',
        detail: 'Led AI research and development for 10 years.',
        startMonth: 3,
        startYear: 2008,
        endMonth: 8,
        endYear: 2018,
      },
    ],
    educations: [
      {
        id: 2,
        name: 'MSc in Electrical Engineering',
        detail: 'Focused on AI and machine learning applications.',
        entranceMonth: 9,
        entranceYear: 1998,
        graduationMonth: 6,
        graduationYear: 2002,
      },
    ],
  },
  {
    userId: 3,
    username: 'william_smith789',
    fullName: 'William Smith',
    email: 'willsmith@example.com',
    phoneNumber: '+5551234567',
    userRole: 'MEMBER',
    userStatus: 'ACTIVE',
    avatar: {
      key: 'avatar789',
      type: 'JPG',
      name: 'avatar.jpg',
      size: 1024,
      url: 'https://static.vecteezy.com/system/resources/thumbnails/028/287/384/small_2x/a-mature-indian-male-doctor-on-a-white-background-ai-generated-photo.jpg',
    },
    expertId: 103,
    name: 'Dr. William Smith',
    affiliation: 'Global Research Group',
    yearOfExperience: 10,
    school: 'School of Medicine',
    department: 'Neurology',
    detailedField: 'Neuroscience',
    expertAttachments: [
      {
        key: 'attachment789',
        type: 'PDF',
        name: 'clinical-study.pdf',
        size: 2048,
        url: 'https://example.com/clinical-study.pdf',
      },
    ],
    businessFields: [
      {
        id: 4,
        name: 'Healthcare',
        isOtherField: false,
      },
    ],
    advisoryFields: [
      {
        id: 3,
        name: 'Brain Research',
        isOtherField: true,
      },
    ],
    experiences: [
      {
        id: 3,
        name: 'Neurologist',
        detail: 'Conducted groundbreaking research on brain disorders.',
        startMonth: 2,
        startYear: 2012,
        endMonth: 5,
        endYear: 2022,
      },
    ],
    educations: [
      {
        id: 3,
        name: 'MD in Neurology',
        detail: 'Specialized in neuroscience.',
        entranceMonth: 8,
        entranceYear: 2006,
        graduationMonth: 6,
        graduationYear: 2010,
      },
    ],
  },
];
