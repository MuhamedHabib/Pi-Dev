<?php

namespace App\Entity;

use App\Repository\MessageRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;


/**
 * Message
 *
 * @ORM\Table(name="message", indexes={@ORM\Index(name="FK_mess_user", columns={"id_user"})})
 * @ORM\Entity(repositoryClass=MessageRepository::class)
 */
class Message
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_message", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idMessage;

    /**
     * @var \DateTime|null
     *
     * @ORM\Column(name="date_creation", type="date", nullable=true)
     */
    private $dateCreation;

    /**
     * @var int|null
     *
     * @ORM\Column(name="id_user", type="integer", nullable=true)
     */
    private $idUser;

    /**
     * @var string
     *
     * @ORM\Column(name="message", type="string", length=99, nullable=false)
     * @Assert\NotBlank(message="Veuillez écrire un message")
     * @Assert\Length(
     *      min="10",
     *      max="100",
     *     minMessage="Doit contenir {{ limit }} caractéres",
     *     maxMessage="Ne doit pas dépasser {{ limit }} caractéres",
     *     )
     */
    private $message;

    /**
     * @var string|null
     *
     * @ORM\Column(name="reponse", type="string", length=99, nullable=true)
     */
    private $reponse;

    /**
     * @var string|null
     *
     * @ORM\Column(name="record", type="string", length=99, nullable=true)
     */
    private $record;

    public function getIdMessage(): ?int
    {
        return $this->idMessage;
    }

    public function getDateCreation(): ?\DateTimeInterface
    {
        return $this->dateCreation;
    }

    public function setDateCreation(\DateTimeInterface $dateCreation): self
    {
        $this->dateCreation = $dateCreation;

        return $this;
    }

    public function getIdUser(): ?int
    {
        return $this->idUser;
    }

    public function setIdUser(?int $idUser): self
    {
        $this->idUser = $idUser;

        return $this;
    }

    public function getMessage(): ?string
    {
        return $this->message;
    }

    public function setMessage(string $message): self
    {
        $this->message = $message;

        return $this;
    }

    public function getReponse(): ?string
    {
        return $this->reponse;
    }

    public function setReponse(?string $reponse): self
    {
        $this->reponse = $reponse;

        return $this;
    }

    public function getRecord(): ?string
    {
        return $this->record;
    }

    public function setRecord(string $record): self
    {
        $this->record = $record;

        return $this;
    }


}
